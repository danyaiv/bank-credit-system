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
        log.info(">> [gRPC ЗАПРОС]: Заявка #{}, Клиент #{}, Запрошено: {} руб, Долг: {} руб",
                request.getApplicationId(),
                request.getClientId(),
                request.getRequestedAmount(),
                request.getCurrentDebt());

        double requestedAmount = request.getRequestedAmount();
        double currentDebt = request.getCurrentDebt();

        // расчет доли текущего долга относительно запрашиваемой суммы
        double debtRatio = requestedAmount > 0 ? (currentDebt / requestedAmount) : 1.0;
        double debtLoadPercent = debtRatio * 100.0;

        boolean isApproved;
        double interestRate;
        String rejectionReason = "";
        String recommendation;

        // если долг превышает 50% от суммы заявки то отказ
        if (debtRatio > 0.50) {
            isApproved = false;
            interestRate = 0.0;
            rejectionReason = String.format("Сумма текущих задолженностей (%.2f руб) превышает 50%% от суммы кредита", currentDebt);
            recommendation = "REJECT";

            log.warn("<< [gRPC ВЕРДИКТ: ОТКАЗ]: Заявка #{}. Причина: {}", request.getApplicationId(), rejectionReason);
        } else {
            // базовая ставка 13.5% + надбавка за риск
            isApproved = true;
            interestRate = 13.5 + (debtRatio * 4.0);
            recommendation = debtRatio < 0.20 ? "AUTO_APPROVE" : "MANUAL_REVIEW";

            log.info("<< [gRPC ВЕРДИКТ: ОДОБРЕНО]: Заявка #{}. Ставка: {}%, Нагрузка: {}%",
                    request.getApplicationId(), String.format("%.2f", interestRate), String.format("%.1f", debtLoadPercent));
        }

        LoanScoringResponse response = LoanScoringResponse.newBuilder()
                .setApplicationId(request.getApplicationId())
                .setClientId(request.getClientId())
                .setIsApproved(isApproved)
                .setInterestRate(Math.round(interestRate * 100.0) / 100.0)
                .setDebtLoadRatio(Math.round(debtLoadPercent * 10.0) / 10.0)
                .setRejectionReason(rejectionReason)
                .setRecommendation(recommendation)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}