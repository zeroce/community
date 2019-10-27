package life.maijiang.community.exception;

public enum CustomizeErrorCode implements ErrorCode {

    QUESTION_NOT_FOUND(2001, "你找的问题不在了，要不要换个试试？？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "当前操作需要登录，请登录后重试！！"),
    SYS_ERROR(2004, "服务器冒烟了！::>_<::哥哥等下再来吧(─.─|||"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在！！！"),
    COMMEMT_NOT_FOUND(2006, "回复的评论不存在，下次早点来哦(⊙o⊙)！！"),
    COMMEMT_IS_EMPTY(2007, "输入内容不能为空！"),
    READ_NOTIFICATION_FAIL(2008, "兄弟，看别人的消息可不对呦~~"),
    NOTIFICATION_NOT_FOUND(2009, "消息通知可能被吃了( ⊙ o ⊙ )"),
    FILE_UPLOAD_FAIL(2010, "图片上传失败！"),
    ;

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

}
