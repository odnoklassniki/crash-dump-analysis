package demo4;

abstract class AbstractTextSearchStrategy {
    char[] characterMap;

    public void setCharacterMap(char[] characterMap) {
        this.characterMap = characterMap;
    }

    char map(char c) {
        return characterMap != null && c < characterMap.length ? characterMap[c] : c;
    }

    public abstract void setSubtext(String subtext);
    public abstract int indexOf(String text);
}
