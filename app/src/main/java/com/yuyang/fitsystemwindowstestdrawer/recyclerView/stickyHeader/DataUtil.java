package com.yuyang.fitsystemwindowstestdrawer.recyclerView.stickyHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试数据生成类
 */
public class DataUtil {
  public static final int MODEL_COUNT = 30;

  public static List<SimpleBean> getData() {
    List<SimpleBean> stickyExampleModels = new ArrayList<>();

    for (int index = 0; index < MODEL_COUNT; index++) {
      if (index < 5) {
        stickyExampleModels.add(new SimpleBean(
            "吸顶文本1", "name" + index, "gender" + index, "profession" + index));
      } else if (index < 15) {
        stickyExampleModels.add(new SimpleBean(
            "吸顶文本2", "name" + index, "gender" + index, "profession" + index));
      } else if (index < 25) {
        stickyExampleModels.add(new SimpleBean(
            "吸顶文本3", "name" + index, "gender" + index, "profession" + index));
      } else {
        stickyExampleModels.add(new SimpleBean(
            "吸顶文本4", "name" + index, "gender" + index, "profession" + index));
      }
    }

    return stickyExampleModels;
  }
}
