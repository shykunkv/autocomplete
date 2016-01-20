package trie;

public class Tuple<T> {

    private String term;
    private T weight;

    public Tuple(String term, T weigth) {
        this.term = term;
        this.weight = weigth;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public T getWeight() {
        return weight;
    }

    public void setWieght(T wiegth) {
        this.weight = wiegth;
    }
}
