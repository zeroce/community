package life.maijiang.community.enums;

public enum FavourTypeEnum {

    TARGET_TO_QUESTION(1),
    TARGET_TO_COMMENT(2),
    ;
    private Integer type;

    public Integer getType() {
        return type;
    }
    FavourTypeEnum(Integer type) {
        this.type = type;
    }

}
