package com.minutch.fox.param.dance;

import com.minutch.fox.param.PageParam;
import lombok.Data;

/**
 * Created by Minutch on 16/3/19.
 */
@Data
public class StudentQueryParam extends PageParam {

    private String keyword;
}
