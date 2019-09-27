package life.maijiang.community.exception;

public enum  CustomizeErrorCode implements ErrorCode {

    QUESTION_NOT_FOUND("你找的问题不在了，要不要换个试试？？");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
