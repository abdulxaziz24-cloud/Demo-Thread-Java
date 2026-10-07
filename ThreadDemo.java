import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;

/**
 * Program sederhana multithreading di Java.
 * Tiga thread berjalan bersamaan dengan tugas yang berbeda:
 *   1. Thread membaca teks (baris demi baris)
 *   2. Thread menghitung faktorial
 *   3. Thread mencari bilangan prima
 * Thread utama (main) menunggu ketiganya selesai memakai join().
 */
public class ThreadDemo {

    // Tugas 1: membaca teks baris per baris
    static class BacaTeks implements Runnable {
        private final String teks;

        BacaTeks(String teks) {
            this.teks = teks;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new StringReader(teks))) {
                String baris;
                int nomor = 1;
                while ((baris = br.readLine()) != null) {
                    System.out.println("[Thread-Baca] Baris " + nomor + ": " + baris);
                    nomor++;
                    Thread.sleep(300); // simulasi proses yang butuh waktu
                }
                System.out.println("[Thread-Baca] Selesai membaca " + (nomor - 1) + " baris.");
            } catch (IOException | InterruptedException e) {
                System.out.println("[Thread-Baca] Error: " + e.getMessage());
            }
        }
    }

    // Tugas 2: menghitung faktorial dari n
    static class HitungFaktorial implements Runnable {
        private final int n;

        HitungFaktorial(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            try {
                BigInteger hasil = BigInteger.ONE;
                for (int i = 1; i <= n; i++) {
                    hasil = hasil.multiply(BigInteger.valueOf(i));
                    System.out.println("[Thread-Faktorial] " + i + "! = " + hasil);
                    Thread.sleep(300);
                }
                System.out.println("[Thread-Faktorial] Hasil akhir " + n + "! = " + hasil);
            } catch (InterruptedException e) {
                System.out.println("[Thread-Faktorial] Error: " + e.getMessage());
            }
        }
    }

    // Tugas 3: mencari bilangan prima dari 2 sampai batas
    static class CariPrima implements Runnable {
        private final int batas;

        CariPrima(int batas) {
            this.batas = batas;
        }

        private boolean isPrima(int x) {
            if (x < 2) return false;
            for (int i = 2; i * i <= x; i++) {
                if (x % i == 0) return false;
            }
            return true;
        }

        @Override
        public void run() {
            try {
                int jumlah = 0;
                for (int i = 2; i <= batas; i++) {
                    if (isPrima(i)) {
                        jumlah++;
                        System.out.println("[Thread-Prima] Ditemukan prima: " + i);
                        Thread.sleep(300);
                    }
                }
                System.out.println("[Thread-Prima] Total prima sampai " + batas + ": " + jumlah);
            } catch (InterruptedException e) {
                System.out.println("[Thread-Prima] Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        String teks = "Sistem operasi mengelola sumber daya komputer.\n"
                + "Thread adalah unit eksekusi terkecil dalam sebuah proses.\n"
                + "Multithreading membuat beberapa tugas berjalan bersamaan.";

        Thread t1 = new Thread(new BacaTeks(teks), "Thread-Baca");
        Thread t2 = new Thread(new HitungFaktorial(10), "Thread-Faktorial");
        Thread t3 = new Thread(new CariPrima(30), "Thread-Prima");

        System.out.println("=== Program dimulai, membuat 3 thread ===");

        t1.start();
        t2.start();
        t3.start();

        try {
            // Mirip pthread_join(): main menunggu thread selesai
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Main terganggu: " + e.getMessage());
        }

        System.out.println("=== Semua thread selesai, program berakhir ===");
    }
}