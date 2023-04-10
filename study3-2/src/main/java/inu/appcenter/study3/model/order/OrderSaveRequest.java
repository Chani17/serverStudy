package inu.appcenter.study3.model.order;

import lombok.Data;

@Data
public class OrderSaveRequest {

    // valitation 처리 해줘야함
    private Long prodctId;

    private Integer count;
}

