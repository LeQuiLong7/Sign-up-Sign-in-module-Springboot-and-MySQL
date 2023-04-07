package com.lql.hellospringsecurity.model.request;

import lombok.Data;

@Data
public class PageRequestDetail {
    private int pageNo;
    private int pageSize;
}
