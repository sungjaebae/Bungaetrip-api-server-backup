package GoGetters.GoGetter.util.masking;

public class Masking {

    public static String mask(MaskingType type, String value) {
        String str = "";
        switch (type) {
            case TITLE:
                str = getTitleMask(value);
                break;
            case PLACE:
                str = getPlaceMask(value);
                break;
            case DATE:
                str = getDateMask(value);
                break;


            default:
                break;
        }
        return str;
    }

    private static String getTitleMask(String value) {
        return "";
    }

    private static String getPlaceMask(String value) {
        return "";
    }
    private static String getDateMask(String value) {
        return "";
    }


}
