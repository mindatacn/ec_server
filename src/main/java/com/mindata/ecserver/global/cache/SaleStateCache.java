package com.mindata.ecserver.global.cache;

import com.mindata.ecserver.main.vo.SaleStateVO;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;

import static com.mindata.ecserver.global.constant.CacheConstant.SALE_STATE_KEY;

/**
 * @author wuweifeng wrote on 2017/12/11.
 */
@Component
public class SaleStateCache extends BaseCache {

    public SaleStateVO getSaleStateVO(String begin, String end) {
        Object object = stringRedisTemplate.opsForValue().get(SALE_STATE_KEY + "_" + begin + "_" + end);
        if (object == null) {
            return null;
        }
        JSONObject jsonObject = JSONUtil.parseObj(object.toString());
        return JSONUtil.toBean(jsonObject, SaleStateVO.class);
    }

    public void saveSaleState(String begin, String end, SaleStateVO saleStateVO) {
        if (saleStateVO == null) {
            return;
        }
        stringRedisTemplate.opsForValue().set(SALE_STATE_KEY + "_" + begin + "_" + end, JSONUtil.toJsonStr
                (saleStateVO));
    }
}
