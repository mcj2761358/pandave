package com.minutch.fox.biz.decoration;

import java.util.List;

import com.minutch.fox.entity.decoration.ReturnOrder;
import com.minutch.fox.param.decoration.ReturnOrderQueryParam;
import com.minutch.fox.view.decoration.ReturnOrderView;

public interface ReturnOrderService {
    List<ReturnOrder> getAll();

    ReturnOrder getById(Long id);

    boolean save(ReturnOrder returnOrder);

    boolean deleteById(Long id);

    int deleteByIds(Long[] ids);

    List<ReturnOrderView> queryReturnOrder(ReturnOrderQueryParam param);

    int queryReturnOrderCount(ReturnOrderQueryParam param);

    List<ReturnOrderView> queryByHeaderId(Long headerId);
}
