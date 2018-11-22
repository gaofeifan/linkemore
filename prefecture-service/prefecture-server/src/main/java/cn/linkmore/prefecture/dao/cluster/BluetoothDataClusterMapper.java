package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;
import cn.linkmore.prefecture.response.ResBluetoothData;

public interface BluetoothDataClusterMapper {

    ResBluetoothData findById(Long id);

	Integer count(Map<String, Object> param);

	List<ResBluetoothData> findPage(Map<String, Object> param);

}