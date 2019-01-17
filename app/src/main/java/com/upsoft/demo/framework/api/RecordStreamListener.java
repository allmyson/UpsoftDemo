package com.upsoft.demo.framework.api;

/**
 * Copyright (c) 2016,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 *
 * 文件名称：L.java<br>
 * 摘要：获取录音音频流-用于扩展<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：董杰科<br>
 * 完成日期：2016年4月26日<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：董杰科<br>
 * 完成日期：2016年4月26日<br>
 */
public interface RecordStreamListener {
    void recordOfByte(byte[] data, int begin, int end);
    void recordAmplitude(double AmlitudeMax);
}
