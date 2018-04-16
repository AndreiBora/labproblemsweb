package ro.ubb.catalog.web.controller.validator;

public class GradingException extends RuntimeException {
    private String msg;

    public GradingException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
