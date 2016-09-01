package com.yuyang.fitsystemwindowstestdrawer.softInput.emotionMode.utils;

import android.support.v4.util.ArrayMap;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

/**
 * 表情加载类,可自己添加多种表情，分别建立不同的map存放和不同的标志符即可
 */
public class EmotionUtils {
    /**
     * 表情类型标志符
     */
    public static final int EMOTION_TUZKI_TYPE = 0x0001;//兔斯基
    public static final int EMOTION_XIHAHOU_TYPE = 0x0002;//嘻哈猴
    /**
     * key-表情文字;
     * value-表情图片资源
     */
    public static ArrayMap<String, Integer> EMOTION_TUZKI;
    public static ArrayMap<String, Integer> EMOTION_XIHAHOU;
    static {
        EMOTION_TUZKI = new ArrayMap<>();
        EMOTION_TUZKI.put("[兔斯基1]", R.mipmap.ic_emotion);
        EMOTION_TUZKI.put("[兔斯基2]", R.mipmap.ic_emotion);
        EMOTION_TUZKI.put("[兔斯基3]", R.mipmap.ic_emotion);
        EMOTION_TUZKI.put("[兔斯基4]", R.mipmap.ic_emotion);
        EMOTION_TUZKI.put("[兔斯基5]", R.mipmap.ic_emotion);
        EMOTION_TUZKI.put("[兔斯基6]", R.mipmap.ic_emotion);
        EMOTION_TUZKI.put("[兔斯基7]", R.mipmap.ic_emotion);
        EMOTION_XIHAHOU = new ArrayMap<>();
        EMOTION_XIHAHOU.put("[嘻哈猴1]", R.mipmap.ic_plus);
        EMOTION_XIHAHOU.put("[嘻哈猴2]", R.mipmap.ic_plus);
        EMOTION_XIHAHOU.put("[嘻哈猴3]", R.mipmap.ic_plus);
        EMOTION_XIHAHOU.put("[嘻哈猴4]", R.mipmap.ic_plus);
        EMOTION_XIHAHOU.put("[嘻哈猴5]", R.mipmap.ic_plus);
        EMOTION_XIHAHOU.put("[嘻哈猴6]", R.mipmap.ic_plus);
        EMOTION_XIHAHOU.put("[嘻哈猴7]", R.mipmap.ic_plus);
    }

    /**
     * 根据名称获取当前表情图标R值
     * @param EmotionType 表情类型标志符
     * @param imgName 名称
     * @return
     */
    public static Integer getImgByName(int EmotionType,String imgName) {
        Integer integer = null;
        switch (EmotionType){
            case EMOTION_TUZKI_TYPE:
                integer = EMOTION_TUZKI.get(imgName);
                break;
            case EMOTION_XIHAHOU_TYPE:
                integer = EMOTION_XIHAHOU.get(imgName);
                break;
            default:
                LogUtils.e("the emojiMap is null!!");
                break;
        }
        return integer == null ? -1 : integer;
    }

    /**
     * 根据类型获取表情数据
     * @param EmotionType
     * @return
     */
    public static ArrayMap<String, Integer> getEmojiMap(int EmotionType){
        ArrayMap EmojiMap = null;
        switch (EmotionType){
            case EMOTION_TUZKI_TYPE:
                EmojiMap = EMOTION_TUZKI;
                break;
            case EMOTION_XIHAHOU_TYPE:
                EmojiMap = EMOTION_XIHAHOU;
                break;
            default:
                EmojiMap = EMOTION_TUZKI;
                break;
        }
        return EmojiMap;
    }

    /**
     * 查找所有表情
     * @param key
     * @return
     */
    public static Integer getImgByName(String key) {
        Integer integer = null;
        integer = EMOTION_TUZKI.get(key);
        if (integer == null){
            integer = EMOTION_XIHAHOU.get(key);
        }
        return integer;
    }
}
