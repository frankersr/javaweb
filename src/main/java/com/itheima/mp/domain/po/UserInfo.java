package com.itheima.mp.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 86178
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UserInfo {
    private Integer age;
    private String intro;
    private String gender;

}
