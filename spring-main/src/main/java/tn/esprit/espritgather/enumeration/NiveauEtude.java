package tn.esprit.espritgather.enumeration;

public enum NiveauEtude {
    FIRST_YEAR, SECOND_YEAR, THIRD_YEAR, FOURTH_YEAR, FIFTH_YEAR, GRADUATED;

    public NiveauEtude next() {
        switch (this) {
            case FIRST_YEAR:
                return SECOND_YEAR;
            case SECOND_YEAR:
                return THIRD_YEAR;
            case THIRD_YEAR:
                return FOURTH_YEAR;
            case FOURTH_YEAR:
                return FIFTH_YEAR;
            case FIFTH_YEAR:
            case GRADUATED:
            default:
                return GRADUATED;
        }
    }



}
