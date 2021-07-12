import java.util.ArrayList;
import java.util.List;

public class NodoSequencia {

    protected List<Nodo> caminhos;
    protected int custoAcumulado;
    protected double heuristica;

    public NodoSequencia() {
        caminhos = new ArrayList<>();
        custoAcumulado = 0;
        heuristica = 0;
    }

    public NodoSequencia(Nodo nodo) {
        caminhos = new ArrayList<>();
        caminhos.add(nodo);
        custoAcumulado = 0;
        heuristica = nodo.heuristica;
    }

    public NodoSequencia proximo(Nodo nodo) {
        List<Nodo> caminhos = new ArrayList<>();
        caminhos.addAll(this.caminhos);
        caminhos.add(nodo);
        NodoSequencia ns = new NodoSequencia();
        ns.caminhos = caminhos;
        ns.custoAcumulado = this.custoAcumulado + 1;
        ns.heuristica = nodo.heuristica;
        return ns;
    }

    public int getCustoAcumulado() {
        return custoAcumulado;
    }

    public double getHeuristica() {
        return heuristica;
    }

    public double getCustoComHeuristica() {
        return custoAcumulado + heuristica;
    }

    public String getEstado() {
        return caminhos.get(caminhos.size()-1).estado;
    }
}
