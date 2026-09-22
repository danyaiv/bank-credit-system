package edu.rutmiit.demo.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.66.0)",
    comments = "Source: bank_analytics.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class BankAnalyticsGrpc {

  private BankAnalyticsGrpc() {}

  public static final java.lang.String SERVICE_NAME = "bankanalytic.BankAnalytics";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<edu.rutmiit.demo.grpc.LoanScoringRequest,
      edu.rutmiit.demo.grpc.LoanScoringResponse> getEvaluateLoanMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "EvaluateLoan",
      requestType = edu.rutmiit.demo.grpc.LoanScoringRequest.class,
      responseType = edu.rutmiit.demo.grpc.LoanScoringResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<edu.rutmiit.demo.grpc.LoanScoringRequest,
      edu.rutmiit.demo.grpc.LoanScoringResponse> getEvaluateLoanMethod() {
    io.grpc.MethodDescriptor<edu.rutmiit.demo.grpc.LoanScoringRequest, edu.rutmiit.demo.grpc.LoanScoringResponse> getEvaluateLoanMethod;
    if ((getEvaluateLoanMethod = BankAnalyticsGrpc.getEvaluateLoanMethod) == null) {
      synchronized (BankAnalyticsGrpc.class) {
        if ((getEvaluateLoanMethod = BankAnalyticsGrpc.getEvaluateLoanMethod) == null) {
          BankAnalyticsGrpc.getEvaluateLoanMethod = getEvaluateLoanMethod =
              io.grpc.MethodDescriptor.<edu.rutmiit.demo.grpc.LoanScoringRequest, edu.rutmiit.demo.grpc.LoanScoringResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "EvaluateLoan"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  edu.rutmiit.demo.grpc.LoanScoringRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  edu.rutmiit.demo.grpc.LoanScoringResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BankAnalyticsMethodDescriptorSupplier("EvaluateLoan"))
              .build();
        }
      }
    }
    return getEvaluateLoanMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BankAnalyticsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BankAnalyticsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BankAnalyticsStub>() {
        @java.lang.Override
        public BankAnalyticsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BankAnalyticsStub(channel, callOptions);
        }
      };
    return BankAnalyticsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BankAnalyticsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BankAnalyticsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BankAnalyticsBlockingStub>() {
        @java.lang.Override
        public BankAnalyticsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BankAnalyticsBlockingStub(channel, callOptions);
        }
      };
    return BankAnalyticsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BankAnalyticsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BankAnalyticsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BankAnalyticsFutureStub>() {
        @java.lang.Override
        public BankAnalyticsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BankAnalyticsFutureStub(channel, callOptions);
        }
      };
    return BankAnalyticsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void evaluateLoan(edu.rutmiit.demo.grpc.LoanScoringRequest request,
        io.grpc.stub.StreamObserver<edu.rutmiit.demo.grpc.LoanScoringResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEvaluateLoanMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service BankAnalytics.
   */
  public static abstract class BankAnalyticsImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return BankAnalyticsGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service BankAnalytics.
   */
  public static final class BankAnalyticsStub
      extends io.grpc.stub.AbstractAsyncStub<BankAnalyticsStub> {
    private BankAnalyticsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BankAnalyticsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BankAnalyticsStub(channel, callOptions);
    }

    /**
     */
    public void evaluateLoan(edu.rutmiit.demo.grpc.LoanScoringRequest request,
        io.grpc.stub.StreamObserver<edu.rutmiit.demo.grpc.LoanScoringResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEvaluateLoanMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service BankAnalytics.
   */
  public static final class BankAnalyticsBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<BankAnalyticsBlockingStub> {
    private BankAnalyticsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BankAnalyticsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BankAnalyticsBlockingStub(channel, callOptions);
    }

    /**
     */
    public edu.rutmiit.demo.grpc.LoanScoringResponse evaluateLoan(edu.rutmiit.demo.grpc.LoanScoringRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEvaluateLoanMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service BankAnalytics.
   */
  public static final class BankAnalyticsFutureStub
      extends io.grpc.stub.AbstractFutureStub<BankAnalyticsFutureStub> {
    private BankAnalyticsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BankAnalyticsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BankAnalyticsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<edu.rutmiit.demo.grpc.LoanScoringResponse> evaluateLoan(
        edu.rutmiit.demo.grpc.LoanScoringRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEvaluateLoanMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_EVALUATE_LOAN = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_EVALUATE_LOAN:
          serviceImpl.evaluateLoan((edu.rutmiit.demo.grpc.LoanScoringRequest) request,
              (io.grpc.stub.StreamObserver<edu.rutmiit.demo.grpc.LoanScoringResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getEvaluateLoanMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              edu.rutmiit.demo.grpc.LoanScoringRequest,
              edu.rutmiit.demo.grpc.LoanScoringResponse>(
                service, METHODID_EVALUATE_LOAN)))
        .build();
  }

  private static abstract class BankAnalyticsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BankAnalyticsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return edu.rutmiit.demo.grpc.BankAnalyticsOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BankAnalytics");
    }
  }

  private static final class BankAnalyticsFileDescriptorSupplier
      extends BankAnalyticsBaseDescriptorSupplier {
    BankAnalyticsFileDescriptorSupplier() {}
  }

  private static final class BankAnalyticsMethodDescriptorSupplier
      extends BankAnalyticsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    BankAnalyticsMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (BankAnalyticsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BankAnalyticsFileDescriptorSupplier())
              .addMethod(getEvaluateLoanMethod())
              .build();
        }
      }
    }
    return result;
  }
}
