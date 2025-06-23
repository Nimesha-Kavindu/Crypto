import java.util.Scanner;

public class RSAAlgorithm {

    static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    static int findModInverse(int e, int phi) {
        for (int d = 1; d < phi; d++) {
            if ((d * e) % phi == 1) return d;
        }
        return -1; 
    }

    static int powerMod(int base, int exp, int mod) {
        int result = 1;
        base = base % mod;
        for (int i = 0; i < exp; i++) {
            result = (result * base) % mod;
        }
        return result;
    }

    static boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int p;
            do {
                System.out.print("Enter first prime number (p): ");
                p = sc.nextInt();
                if (!isPrime(p)) {
                    System.out.println("Not a prime! Please enter a prime number.");
                }
            } while (!isPrime(p));

            int q;
            do {
                System.out.print("Enter second prime number (q): ");
                q = sc.nextInt();
                if (!isPrime(q)) {
                    System.out.println("Not a prime! Please enter a prime number.");
                }
            } while (!isPrime(q));

            int n = p * q;
            int phi = (p - 1) * (q - 1);

            int e = 2;
            while (e < phi) {
                if (gcd(e, phi) == 1) break;
                e++;
            }

            int d = findModInverse(e, phi);
            if (d == -1) {
                System.out.println("Error: Could not find a modular inverse for e = " + e);
                return;
            }

            System.out.println("\n--- RSA Key Generation ---");
            System.out.println("φ(n): " + phi);
            System.out.println("Public Key  (e, n): (" + e + ", " + n + ")");
            System.out.println("Private Key (d, n): (" + d + ", " + n + ")");

            System.out.print("\nEnter a message to encrypt (as a number): ");
            int message = sc.nextInt();

            if (message >= n) {
                System.out.println("Error: Message must be less than n (" + n + ").");
                return;
            }

            int encrypted = powerMod(message, e, n);
            int decrypted = powerMod(encrypted, d, n);

            System.out.println("Encrypted Message: " + encrypted);
            System.out.println("Decrypted Message: " + decrypted);
        }
    }
}
