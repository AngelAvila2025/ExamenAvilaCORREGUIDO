import java.util.*;

public class Main {
    static class Ticket {
        private int id;
        private String level;
        private String desc;

        public Ticket(int id, String level, String desc) {
            this.id = id;
            this.level = level;
            this.desc = desc;
        }

        public String getLevel() {
            return level;
        }

        public String toString() {
            return "Ticket{id=" + id + ", level=" + level + ", desc=\"" + desc + "\"}";
        }
    }

    static class Utils {
        List<Ticket> tickets;
        Queue<Ticket> highQ;
        Queue<Ticket> normalQ;
        Stack<Ticket> served;

        public Utils() {
            tickets = new ArrayList<>();
            highQ = new LinkedList<>();
            normalQ = new LinkedList<>();
            served = new Stack<>();
        }

        // Clasificación recursiva
        public void classifyRec(List<Ticket> list, int idx, Queue<Ticket> highQ, Queue<Ticket> normalQ) {
            if (idx == list.size()) return;

            Ticket t = list.get(idx);
            if (t.getLevel().equalsIgnoreCase("HIGH")) {
                highQ.offer(t);
            } else {
                normalQ.offer(t);
            }

            classifyRec(list, idx + 1, highQ, normalQ);
        }

        // Atención recursiva
        public void attendQueueRec(Queue<Ticket> q, Stack<Ticket> served) {
            if (q.isEmpty()) return;

            Ticket current = q.poll();
            System.out.println("— Atendiendo: " + current);
            served.push(current);

            attendQueueRec(q, served);
        }
    }

    public static void main(String[] args) {
        Utils utils = new Utils();

        utils.tickets.add(new Ticket(1, "HIGH", "Falla crítica"));
        utils.tickets.add(new Ticket(2, "NORMAL", "Consulta general"));
        utils.tickets.add(new Ticket(3, "HIGH", "Corte del servicio"));
        utils.tickets.add(new Ticket(4, "NORMAL", "Solicitud de info"));
        utils.tickets.add(new Ticket(5, "NORMAL", "Duda de facturación"));
        utils.tickets.add(new Ticket(6, "HIGH", "Urgente de cliente VIP"));

        System.out.println("— Lista original —");
        for (Ticket t : utils.tickets) {
            System.out.println(t);
        }

        utils.classifyRec(utils.tickets, 0, utils.highQ, utils.normalQ);

        System.out.println("\nAtendiendo HIGH");
        utils.attendQueueRec(utils.highQ, utils.served);

        System.out.println("\nAtendiendo NORMAL");
        utils.attendQueueRec(utils.normalQ, utils.served);

        System.out.println("\nHistorial (Stack top→bottom):");
        System.out.println(utils.served);
    }
}