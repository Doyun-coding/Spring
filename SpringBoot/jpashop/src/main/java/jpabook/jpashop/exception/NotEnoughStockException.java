package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException() {
    }

    public NotEnoughStockException(String message) {
        super(message); // 메세지 넘겨줌
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause); // 메세지 + 원인
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

}
