package by.incubator.Entity.Vehicle;

public enum Color {

        RED("RED"), BLUE("BLUE"), GREEN("GREEN"), WHITE("WHITE"), GRAY("GRAY"), YELLOW("YELLOW"), BLACK("BLACK");
        private String name;

        Color(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

    @Override
    public String toString() {
        return "Color." + name ;
    }

    public static Color valueOfLabel(String label) {
        for (Color e : values()) {
            if (e.name.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
