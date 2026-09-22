package edu.rutmiit.demo.grpcanalytics.config;

import edu.rutmiit.demo.grpcanalytics.service.CreditScoringService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GrpcServerLifecycle implements SmartLifecycle {

    private static final Logger log = LoggerFactory.getLogger(GrpcServerLifecycle.class);

    @Value("${grpc.server.port:9090}")
    private int port;

    private final CreditScoringService creditScoringService;
    private Server server;
    private boolean running = false;

    public GrpcServerLifecycle(CreditScoringService creditScoringService) {
        this.creditScoringService = creditScoringService;
    }

    @Override
    public void start() {
        try {
            server = ServerBuilder.forPort(port)
                    .addService(creditScoringService) // <-- с маленькой буквы!
                    .build()
                    .start();

            running = true;
            log.info(">> [gRPC СЕРВЕР ЗАПУЩЕН]: Порт {}, сервис скоринга готов к работе", port);
        } catch (IOException e) {
            log.error("Не удалось запустить gRPC сервер: {}", e.getMessage(), e);
        }
    }

    @Override
    public void stop() {
        if (server != null) {
            log.info("Остановка gRPC сервера...");
            server.shutdown();
            running = false;
            log.info("gRPC сервер успешно остановлен");
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}