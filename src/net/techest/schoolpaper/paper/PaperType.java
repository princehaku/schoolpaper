/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.techest.schoolpaper.paper;

/**纸片的类型
 *
 * @author princehaku
 */
public enum PaperType {

    BAIKE,
    XINWEN,
    SUIPIAN,
    QITA;

    public static PaperType parseType(int parseInt) {
        switch (parseInt) {
            case 0:
                return QITA;
            case 1:
                return BAIKE;
            case 2:
                return XINWEN;
            case 3:
                return SUIPIAN;
        }
        return QITA;
    }
}
