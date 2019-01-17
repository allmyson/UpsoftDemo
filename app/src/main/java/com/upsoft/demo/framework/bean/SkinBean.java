package com.upsoft.demo.framework.bean;

import com.upsoft.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename SkinBean
 * @description -------------------------------------------------------
 * @date 2018/10/24 17:30
 */
public class SkinBean {
    public int drawableId;
    public String color;
    public String name;

    public SkinBean() {
    }

    public SkinBean(int drawableId, String color, String name) {
        this.drawableId = drawableId;
        this.color = color;
        this.name = name;
    }

    public static List<SkinBean> getDefaultSkinList() {
        List<SkinBean> list = new ArrayList<>();
        list.add(new SkinBean(R.mipmap.bg_black, "#131313", "骑士黑"));
        list.add(new SkinBean(R.mipmap.bg_gray, "#797979", "古典灰"));
        list.add(new SkinBean(R.mipmap.bg_blue, "#A1EEFE", "倩影蓝"));
        list.add(new SkinBean(R.mipmap.bg_fen, "#F26B89", "俏丽粉"));
        list.add(new SkinBean(R.mipmap.bg_red, "#C10001", "秋枫红"));
        list.add(new SkinBean(R.mipmap.bg_orange, "#F8B004", "阳光橙"));
        list.add(new SkinBean(R.mipmap.bg_zi, "#580A48", "水晶紫"));
        list.add(new SkinBean(R.mipmap.bg_gold, "#FFFF01", "香槟金"));
        list.add(new SkinBean(R.mipmap.bg_green, "#236700", "翡翠绿"));
        return list;
    }
}
