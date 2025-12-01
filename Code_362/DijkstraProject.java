import java.util.*;

public class DijkstraProject {

    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static class Node implements Comparable<Node> {
        int vertex;
        int distance;

        Node(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of nodes: ");
        int n = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int m = sc.nextInt();

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        System.out.println("Enter each edge as: fromNode  toNode  weight (0-based indices, non-negative weight)");
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();

            if (u < 0 || u >= n || v < 0 || v >= n) {
                System.out.println("Invalid edge: " + u + " " + v + ". Skipping this edge.");
                continue;
            }

            graph.get(u).add(new Edge(v, w));
            graph.get(v).add(new Edge(u, w));
        }

        int start = 0; 

        int[] dist = new int[n];
        int[] parent = new int[n];
        dijkstra(graph, start, dist, parent);

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

    public static void dijkstra(List<List<Edge>> graph, int start, int[] dist, int[] parent) {
        int n = graph.size();
        boolean[] visited = new boolean[n];

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

    public static void printPath(int target, int[] parent) {
        List<Integer> path = new ArrayList<>();
        int current = target;

        while (current != -1) {
            path.add(current);
            current = parent[current];
        }

        Collections.reverse(path);

        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
    }
}

