package sec.portscanner;

import java.io.PrintWriter;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class PortScanner {

    final static int MIN_PORT = 1024;
    final static int MAX_PORT = 49151;

    public static void main(String[] args) throws Exception {
        Scanner reader = new Scanner(System.in);

        System.out.println("Which address should I scan?");
        String address = reader.nextLine();
        System.out.println("Start at port?");
        int start = Integer.parseInt(reader.nextLine());
        System.out.println("End at port?");
        int end = Integer.parseInt(reader.nextLine());

        Set<Integer> ports = getAccessiblePorts(address, start, end);
        System.out.println("");

        if (ports.isEmpty()) {
            System.out.println("None found :(");
        } else {
            System.out.println("Open ports list:");
            ports.stream().forEach(p -> System.out.println("\t" + p));
        }
    }

    public static Set<Integer> getAccessiblePorts(String address, int start, int end) {
        Set<Integer> accessiblePorts = new TreeSet<>();
        start = Math.max(start, MIN_PORT);
        end = Math.min(end, MAX_PORT);
        int timeout = 200;
        for(int iTestedPort = start; iTestedPort<=end;iTestedPort++){
           //Source: https://stackoverflow.com/questions/11547082/fastest-way-to-scan-ports-with-java
           try {
             Socket socket = new Socket();
             System.out.println("Scanning port \t" + iTestedPort);
             socket.connect(new InetSocketAddress(address, iTestedPort), timeout);
             socket.close();
             System.out.println("Scan completed: OPEN\n");
             accessiblePorts.add(iTestedPort);
           } catch (Exception ex) {
               System.out.println("Scan completed: CLOSE\n");
           }
        }
        return accessiblePorts;
    }
}
