package com.yehuijie.homophone.mapper.customer;

import com.yehuijie.homophone.entity.Homophone;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户Mapper
 *
 * @author yhj
 * @date 2018-07-12
 */
@Repository
public interface CustomerHomophoneMapper {

    @Select("<script>" +
            " select h.*,y.sort from homophone h left join yun_mu_sort y on y.yun_mu=h.yun_mu " +
            " order by y.sort " +
            "</script>")
    List<Homophone> getCustomerHomPhone();


}
