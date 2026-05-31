import java.util.*;

public class APE4_Grafos {

    // ═══════════════════════════════════════
    // Nodo
    // ═══════════════════════════════════════
    static class Nodo {
        String id;
        String nombre;

        public Nodo(String id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }
    }

    // ═══════════════════════════════════════
    // Arista
    // ═══════════════════════════════════════
    static class Arista {
        String destino;
        int peso;

        public Arista(String destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }

    // ═══════════════════════════════════════
    // Grafo
    // ═══════════════════════════════════════
    static class Grafo {

        Map<String, Nodo> nodos = new HashMap<>();
        Map<String, List<Arista>> adyacencia = new HashMap<>();

        // ═══════════════════════════════════
        // TODO 1
        // Agregar nodo al grafo
        // ═══════════════════════════════════
        public void agregarNodo(String id, String nombre) {

            // Crear y guardar el nodo
            nodos.put(id, new Nodo(id, nombre));

            // Inicializar su lista de vecinos vacía
            adyacencia.put(id, new ArrayList<>());


        }

        // ═══════════════════════════════════
        // TODO 2
        // Agregar arista no dirigida
        // ═══════════════════════════════════
        public void agregarArista(String origen, String destino, int peso) {

            // Agregar conexión origen -> destino
            adyacencia.get(origen).add(new Arista(destino, peso));

            // Agregar conexión destino -> origen (no dirigida)
            adyacencia.get(destino).add(new Arista(origen, peso));


        }

        // ═══════════════════════════════════
        // TODO 3 — BFS
        // Ruta con menos paradas
        // ═══════════════════════════════════
        public List<String> bfs(String inicio, String fin) {

            // Cola para recorrer niveles
            Queue<List<String>> cola = new LinkedList<>();

            // Nodos visitados
            Set<String> visitados = new HashSet<>();

            // Camino inicial
            List<String> caminoInicial = new ArrayList<>();

            // Agregar nodo inicio al camino inicial
            caminoInicial.add(inicio);

            // Agregar caminoInicial a la cola
            cola.add(caminoInicial);

            // Marcar inicio como visitado
            visitados.add(inicio);

            while (!cola.isEmpty()) {

            // Obtener el primer camino de la cola
            List<String> camino = cola.poll();

            String actual = camino.get(camino.size() - 1);

            if (actual.equals(fin)) {
                return camino;
            }

            for (Arista arista : adyacencia.get(actual)) {

                // Verificar si el vecino NO fue visitado
                if (!visitados.contains(arista.destino)) {

                    // Marcar vecino como visitado
                    visitados.add(arista.destino);

                    List<String> nuevoCamino = new ArrayList<>(camino);

                    // Agregar vecino al nuevo camino
                    nuevoCamino.add(arista.destino);

                    // Agregar nuevoCamino a la cola
                    cola.add(nuevoCamino);
                }
            }
        }
            return null;
        }

        // ═══════════════════════════════════
        // TODO 4 — Dijkstra
        // Ruta con menor distancia
        // ═══════════════════════════════════
        public List<String> dijkstra(String inicio, String fin) {

            Map<String, Integer> distancias =
                    new HashMap<>();

            Map<String, String> anteriores =
                    new HashMap<>();

            PriorityQueue<String> cola =
                    new PriorityQueue<>(
                            Comparator.comparingInt(
                                    distancias::get
                            )
                    );

            // Inicializar distancias
            for (String nodo : nodos.keySet()) {
                distancias.put(nodo, Integer.MAX_VALUE);
            }

            // Distancia del inicio = 0
            distancias.put(inicio, 0);

            // Agregar inicio a la cola
            cola.add(inicio);

            while (!cola.isEmpty()) {

                // Obtener nodo con menor distancia
                String actual = cola.poll();

                for (Arista arista : adyacencia.get(actual)) {

                    // Calcular nueva distancia
                    int nuevaDistancia = distancias.get(actual) + arista.peso;

                    // Verificar si nuevaDistancia es menor
                    if (nuevaDistancia < distancias.get(arista.destino)) {

                        // Actualizar distancia
                        distancias.put(arista.destino, nuevaDistancia);

                        // Guardar nodo anterior
                        anteriores.put(arista.destino, actual);

                        // Agregar vecino a la cola
                        cola.add(arista.destino);
                    }
                }
            }

            // Reconstruir camino
            List<String> camino = new ArrayList<>();

            String actual = fin;

            while (actual != null) {

                camino.add(0, actual);

                actual = anteriores.get(actual);
            }

            return camino;
        }

        // ═══════════════════════════════════
        // Mostrar resultado
        // ═══════════════════════════════════
        public void mostrarRuta(List<String> ruta) {

            if (ruta == null) {
                System.out.println("No existe ruta");
                return;
            }

            for (int i = 0; i < ruta.size(); i++) {

                String idNodo = ruta.get(i);

                Nodo nodo = nodos.get(idNodo);

                System.out.print(
                    nodo.nombre + " (" + nodo.id + ")"
                );

                if (i < ruta.size() - 1) {
                    System.out.print(" -> ");
                }
            }

            System.out.println();
        }
    }

    // ═══════════════════════════════════════
    // MAIN
    // ═══════════════════════════════════════
    public static void main(String[] args) {

        Grafo grafo = new Grafo();

        // NODOS
        grafo.agregarNodo("uta", "Universidad");
        grafo.agregarNodo("fisei", "FISEI");
        grafo.agregarNodo("idiomas", "Idiomas");
        grafo.agregarNodo("biblioteca", "Biblioteca");
        grafo.agregarNodo("estadio", "Estadio");
        grafo.agregarNodo("comedor", "Comedor");

        // ARISTAS
        grafo.agregarArista("uta", "fisei", 50);
        grafo.agregarArista("fisei", "idiomas", 40);
        grafo.agregarArista("idiomas", "biblioteca", 30);
        grafo.agregarArista("biblioteca", "estadio", 70);

        // Ruta con menos paradas
        // pero más distancia
        grafo.agregarArista("uta", "comedor", 20);
        grafo.agregarArista("comedor", "estadio", 200);

        // ═══════════════════════════════════
        // PRUEBAS
        // ═══════════════════════════════════

        System.out.println("===== BFS =====");

        List<String> rutaBFS =
                grafo.bfs("uta", "estadio");

        grafo.mostrarRuta(rutaBFS);

        System.out.println("\n===== DIJKSTRA =====");

        List<String> rutaDijkstra =
                grafo.dijkstra("uta", "estadio");

        grafo.mostrarRuta(rutaDijkstra);
    }
}