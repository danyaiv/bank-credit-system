package edu.rutmiit.demo.grpcanalytics.service;

import edu.rutmiit.demo.grpc.BankAnalyticsGrpc;
import edu.rutmiit.demo.grpc.LoanScoringRequest;
import edu.rutmiit.demo.grpc.LoanScoringResponse;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreditScoringService extends BankAnalyticsGrpc.BankAnalyticsImplBase {

    private static final Logger log = LoggerFactory.getLogger(CreditScoringService.class);

    @Override
    public void evaluateLoan(LoanScoringRequest request, StreamObserver<LoanScoringResponse> responseObserver) {
        long appId = request.getApplicationId();
        double amount = request.getRequestedAmount();
        double income = request.getMonthlyIncome();
        double currentDebt = request.getCurrentDebt();

        log.info(">> [gRPC СКОРИНГ ЦБ РФ]: Заявка #{}, Запрошено: {} руб, Доход: {} руб, Текущий долг: {} руб",
                appId, amount, income, currentDebt);

        // Защита от нулевого дохода
        if (income <= 0) {
            income = 1.0;
        }

        // 1. Расчет ежемесячного платежа по новому кредиту (срок 24 мес, ставка 14%)
        double termMonths = 24.0;
        double monthlyNewPrincipal = amount / termMonths;
        double monthlyNewInterest = amount * (0.14 / 12.0);
        double monthlyNewPayment = monthlyNewPrincipal + monthlyNewInterest;

        // 2. Расчет среднемесячного платежа по имеющимся долгам (10% по методике ЦБ РФ)
        double monthlyOldDebtPayment = currentDebt * 0.10;

        // 3. Расчет ПДН (Показатель Долговой Нагрузки)
        double totalMonthlyPayments = monthlyNewPayment + monthlyOldDebtPayment;
        double pdnRaw = (totalMonthlyPayments / income) * 100.0;

        // Округляем до 1 знака (например, 14.5%)
        double pdnPercent = Math.round(pdnRaw * 10.0) / 10.0;

        boolean isApproved;
        double interestRate;
        String rejectionReason = "";
        String recommendation;

        // 4. Оценка по нормативу Банка России (порог 50%)
        if (pdnPercent > 50.0) {
            isApproved = false;
            interestRate = 0.0;
            rejectionReason = String.format("Отказ по нормативу ЦБ РФ: ПДН составляет %.1f%% (допустимый лимит до 50%%)", pdnPercent);
            recommendation = "REJECT";

            log.warn("<< [СКОРИНГ ЦБ РФ: ОТКАЗ]: Заявка #{}. ПДН: {}%. Платежи: {} руб при доходе {} руб",
                    appId, pdnPercent, Math.round(totalMonthlyPayments), Math.round(income));
        } else {
            isApproved = true;
            if (pdnPercent <= 35.0) {
                interestRate = 12.9;
                recommendation = "AUTO_APPROVE";
            } else {
                interestRate = 16.5;
                recommendation = "MANUAL_REVIEW";
            }

            log.info("<< [СКОРИНГ ЦБ РФ: ОДОБРЕНО]: Заявка #{}. ПДН: {}%. Ставка: {}%",
                    appId, pdnPercent, interestRate);
        }

        // 5. Формируем Protobuf ответ
        LoanScoringResponse response = LoanScoringResponse.newBuilder()
                .setApplicationId(appId)
                .setClientId(request.getClientId())
                .setIsApproved(isApproved)
                .setInterestRate(interestRate)
                .setDebtLoadRatio(pdnPercent)
                .setRejectionReason(rejectionReason)
                .setRecommendation(recommendation)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}