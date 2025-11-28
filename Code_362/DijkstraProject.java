import java.util.*;

/**
 * IS362 Project - Dijkstra Shortest Path (Undirected Graph)
 * Starting node is always node 0.
 *
 * Input format (example):
 *   Enter number of nodes: 5
 *   Enter number of edges: 6
 *   Enter each edge as: u v w (0-based nodes, integer weight)
 *   0 1 10
 *   0 2 3
 *   1 2 1
 *   1 3 2
 *   2 3 8
 *   3 4 7
 *
 * Output:
 *   Shortest path from 0 to 0 (cost = 0): 0
 *   Shortest path from 0 to 1 (cost = ...): 0 -> ... -> 1
 *   ...
 */
public class DijkstraProject {

    // Edge class to store neighbor and weight
    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    // Node class used in the priority queue
    static class Node implements Comparable<Node> {
        int vertex;
        int distance;

        Node(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Read number of nodes and edges
        System.out.print("Enter number of nodes: ");
        int n = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int m = sc.nextInt();

        // 2. Build adjacency list for undirected graph
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        System.out.println("Enter each edge as: fromNode  toNode  weight (0-based indices, non-negative weight)");
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();

            // Basic validation (optional but safer)
            if (u < 0 || u >= n || v < 0 || v >= n) {
                System.out.println("Invalid edge: " + u + " " + v + ". Skipping this edge.");
                continue;
            }

            // Undirected: add both directions
            graph.get(u).add(new Edge(v, w));
            graph.get(v).add(new Edge(u, w));
        }

        int start = 0; // starting node is always 0 as per project

        // 3. Run Dijkstra
        int[] dist = new int[n];
        int[] parent = new int[n];
        dijkstra(graph, start, dist, parent);

        // 4. Print shortest paths and costs
        System.out.println("\n=== Shortest paths from node " + start + " ===");
        for (int target = 0; target < n; target++) {
            if (dist[target] == Integer.MAX_VALUE) {
                System.out.println("Node " + target + " is unreachable from node " + start);
            } else {
                System.out.print("Shortest path from " + start + " to " + target +
                        " (cost = " + dist[target] + "): ");
                printPath(target, parent);
                System.out.println();
            }
        }

        sc.close();
    }

    /**
     * Dijkstra's algorithm for non-negative weights.
     */
    public static void dijkstra(List<List<Edge>> graph, int start, int[] dist, int[] parent) {
        int n = graph.size();
        boolean[] visited = new boolean[n];

        // Initialize distances to infinity and parents to -1
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[start] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;

            if (visited[u]) continue;
            visited[u] = true;

            for (Edge edge : graph.get(u)) {
                int v = edge.to;
                int w = edge.weight;

                if (!visited[v] && dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    parent[v] = u;
                    pq.offer(new Node(v, dist[v]));
                }
            }
        }
    }

    /**
     * Print path from start (0) to target using parent array.
     */
    public static void printPath(int target, int[] parent) {
        List<Integer> path = new ArrayList<>();
        int current = target;

        while (current != -1) {
            path.add(current);
            current = parent[current];
        }

        // Reverse path to start from 0
        Collections.reverse(path);

        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
    }
}
