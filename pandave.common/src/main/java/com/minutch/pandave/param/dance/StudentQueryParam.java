package com.minutch.pandave.param.dance;

import com.minutch.pandave.param.PageParam;
import lombok.Data;

/**
 * Created by Minutch on 16/3/19.
 */
@Data
public class StudentQueryParam extends PageParam {

    private String keyword;
}
