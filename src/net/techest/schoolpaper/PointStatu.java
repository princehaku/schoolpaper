/*  Copyright 2010 princehaku
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Created on : 2010-10-26, 23:30:30
 *  Author     : princehaku
 */
package net.techest.schoolpaper;

/**按下屏幕的点的状态
 *
 * @author princehaku
 */
public class PointStatu {

    public static float lastX = 0f;
    public static float lastY = 0f;
    public static float X = 0f;
    public static float Y = 0f;
    public static float minX = 320f;
    public static float maxX = 0f;
    public static float minY = 480f;
    public static float maxY = 0f;

    static void reset() {
        PointStatu.lastX = 0f;
        PointStatu.lastY = 0f;
        PointStatu.X = 0f;
        PointStatu.Y = 0f;
        minX = 320f;
        maxX = 0f;
        minY = 480f;
        maxY = 0f;
    }

    /**更新点状态
     *
     * @param x
     * @param y
     */
    static void updatePoint(float x, float y) {
        PointStatu.lastX = PointStatu.X;
        if (PointStatu.X > maxX) {
            maxX = PointStatu.X;
        }
        if (PointStatu.X < minX) {
            minX = PointStatu.X;
        }
        PointStatu.lastY = PointStatu.Y;
        if (PointStatu.Y > maxY) {
            maxY = PointStatu.Y;
        }
        if (PointStatu.Y < minY) {
            minY = PointStatu.Y;
        }
    }
}
