package cn.linkmore.prefecture.dao.master;

import cn.linkmore.prefecture.entity.BluetoothData;

public interface BluetoothDataMasterMapper {
    int delete(Long id);

    int save(BluetoothData record);
    
}