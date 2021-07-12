import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HeuristicaSimples {

    protected static final String goal = "123456780";
    protected static String estadoInicial;

    public static void main(String[] args) {
        System.out.println("Digite o estado inicial, inserindo 0 no lugar do espaço vazio. (Exemplo: estado final é 123456780)");
        Scanner sc = new Scanner(System.in);
        try {
            String input = sc.nextLine();
            while (!estadoValido(input)) {
                System.out.println("Estado inválido. Tente novamente.");
                input = sc.nextLine();
            }
            estadoInicial = input;
            System.out.println("Buscando...");
            buscaHeuristica();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void buscaHeuristica() {
        List<NodoSequencia> visitados = new ArrayList<>();
        Nodo nInicial = new Nodo(estadoInicial, calculaHeuristicaSimples(estadoInicial), "inicio");
        NodoSequencia nsAtual = new NodoSequencia(nInicial);
        List<NodoSequencia> fronteira = new ArrayList<>();

        String estadoAtual = estadoInicial;
        String caminhoAtual = nInicial.caminho;
        while(!estadoAtual.equals(goal)) {
            for(Nodo alcancavel : calculaAlcance(estadoAtual, caminhoAtual)) {
                fronteira.add(nsAtual.proximo(alcancavel));
            }
            fronteira = fronteira.stream().sorted(Comparator.comparingDouble(NodoSequencia::getCustoComHeuristica).thenComparingDouble(NodoSequencia::getHeuristica)).collect(Collectors.toList());
            NodoSequencia pop = fronteira.remove(0);
            visitados.add(pop);
            estadoAtual = pop.getEstado();
            caminhoAtual = pop.getCaminho();
            nsAtual = pop;
            System.out.println("Estado " + estadoAtual + ", custo " + pop.custoAcumulado + ", heuristica " + pop.heuristica + ", custo total " + pop.getCustoComHeuristica());
        }

        System.out.println("Melhor caminho obtido, " + (nsAtual.caminhos.size()-1) + " movimentos:");
        for (Nodo caminho : nsAtual.caminhos) {
            System.out.println("---");
            System.out.println(caminho.estado.substring(0,3));
            System.out.println(caminho.estado.substring(3,6));
            System.out.println(caminho.estado.substring(6,9));
        }
        System.out.println("---");
    }

    public static List<Nodo> calculaAlcance(String estado, String caminho) {
        List<Nodo> alcance = new ArrayList<>();
        int posVazio = estado.indexOf('0');
        if (posVazio%3 != 2 && caminho != "esquerda") { //diferente de ultima coluna
            char toMove = estado.charAt(posVazio+1);
            String direita = estado.replace('0', '@')
                    .replace(toMove, '0')
                    .replace('@', toMove);
            alcance.add(new Nodo(direita, calculaHeuristicaSimples(direita), "direita"));
        }
        if (posVazio%3 != 0 && caminho != "direita") { //diferente de primeira coluna
            char toMove = estado.charAt(posVazio-1);
            String esquerda = estado.replace('0', '@')
                    .replace(toMove, '0')
                    .replace('@', toMove);
            alcance.add(new Nodo(esquerda, calculaHeuristicaSimples(esquerda), "esquerda"));
        }
        if (posVazio/3 != 2 && caminho != "sobe") { //diferente de ultima linha
            char toMove = estado.charAt(posVazio+3);
            String desce = estado.replace('0', '@')
                    .replace(toMove, '0')
                    .replace('@', toMove);
            alcance.add(new Nodo(desce, calculaHeuristicaSimples(desce), "desce"));
        }
        if (posVazio/3 != 0 && caminho != "desce") { //diferente de primeira linha
            char toMove = estado.charAt(posVazio-3);
            String sobe = estado.replace('0', '@')
                    .replace(toMove, '0')
                    .replace('@', toMove);
            alcance.add(new Nodo(sobe, calculaHeuristicaSimples(sobe), "sobe"));
        }

        return alcance;
    }

    public static double calculaHeuristicaSimples(String estado) {
        double sum = 0;
        for (int i = 0; i < 9; i++) {
            if (estado.charAt(i) != (char)((i+1)%9+'0'))
                sum++;
        }
        return sum/2;
    }

    public static boolean estadoValido(String estado) {
        return estado.length() == 9
                && estado.contains("1")
                && estado.contains("2")
                && estado.contains("3")
                && estado.contains("4")
                && estado.contains("5")
                && estado.contains("6")
                && estado.contains("7")
                && estado.contains("8")
                && estado.contains("0");
    }
}
