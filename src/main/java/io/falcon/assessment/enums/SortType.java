package io.falcon.assessment.enums;

public enum SortType {
    ASCENDING("ASC"),
    DESCENDING("DESC");

    private String sortType;

    SortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSortType() {
        return sortType;
    }

}
