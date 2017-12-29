package com.shidou.hotelsharing;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * 热更新入口
 * <p>
 * Created by mapsh on 2017/3/24.
 */

public class TinkerApplicationEntry extends TinkerApplication {
    public TinkerApplicationEntry() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.shidou.hotelsharing.AppLike", "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
