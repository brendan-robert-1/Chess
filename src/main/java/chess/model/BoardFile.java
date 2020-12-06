package chess.model;

public enum BoardFile {
    a("a"),b("b"),c("c"),d("d"),e("e"),f("f"),g("g"),h("h");
    private String file;

    BoardFile(String file){
        this.file = file;
    }

    public String getFile(){
        return this.file;
    }

    public int getNumericalFile(){
        switch(file) {
            case "a":
               return 1;
            case "b":
                return 2;
            case "c":
                return 3;
            case "d":
                return 4;
            case "e":
                return 5;
            case "f":
                return 6;
            case "g":
                return 7;
            case "h":
                return 8;
            default : throw new IllegalArgumentException("invalid boardfile enum " + file);
        }
    }

    public static BoardFile boardFile(String file){
        switch(file) {
            case "a":
                return BoardFile.a;
            case "b":
                return BoardFile.b;
            case "c":
                return BoardFile.c;
            case "d":
                return BoardFile.d;
            case "e":
                return BoardFile.e;
            case "f":
                return BoardFile.f;
            case "g":
                return BoardFile.g;
            case "h":
                return BoardFile.h;
            default : throw new IllegalArgumentException("invalid boardfile enum " + file);
        }
    }

    public static BoardFile boardFile(int oneIndexedFile){
        if(oneIndexedFile >8 || oneIndexedFile <1){
            throw new IllegalArgumentException("invalid one indexed file");
        }
        switch(oneIndexedFile){
            case 1: return BoardFile.a;
            case 2: return BoardFile.b;
            case 3: return BoardFile.c;
            case 4: return BoardFile.d;
            case 5: return BoardFile.e;
            case 6: return BoardFile.f;
            case 7: return BoardFile.g;
            case 8: return BoardFile.h;
            default: return null;
        }
    }

}
