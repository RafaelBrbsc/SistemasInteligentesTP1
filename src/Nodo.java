public class Nodo {

    protected String estado;
    protected double heuristica;
    protected String caminho;

    public Nodo(String _estado, double _heuristica, String _caminho) {
        estado = _estado;
        heuristica = _heuristica;
        caminho = _caminho;
    }
}
