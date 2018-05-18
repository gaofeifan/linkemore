package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.StallRecharge;
/**
 * dao 车位指定
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface StallRechargeMasterMapper {
    int delete(Long id);

    int save(StallRecharge record);

    int update(StallRecharge record);
}