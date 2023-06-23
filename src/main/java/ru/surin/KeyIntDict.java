package ru.surin;

public enum KeyIntDict {
    F1(82, "F1"),
    F2(83, "F2"),
    F3(84, "F3"),
    F4(85, "F4"),
    F5(86, "F5"),
    F6(87, "F6"),
    F7(88, "F7"),
    F8(89, "F8"),
    F9(90, "F9"),
    F10(91, "F10"),
    F11(92, "F11"),
    F12(93, "F12");

    private final int value;
    private final String keyCode;

    KeyIntDict(int value, String keyCode) {
        this.value = value;
        this.keyCode = keyCode;
    }

    public int getValue() {
        return value;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public static KeyIntDict fromCode(String keyCode) {
        for (KeyIntDict keyIntDict : KeyIntDict.values()) {
            if (keyIntDict.getKeyCode().equalsIgnoreCase(keyCode)) {
                return keyIntDict;
            }
        }
        return null;
    }
}
