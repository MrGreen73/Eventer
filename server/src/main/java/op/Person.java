package op;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by user on 21.04.2018.
 */
public class Person {
    //private static final Map<byte[], byte[]> cache = new HashMap<byte[], byte[]>();

    public static void main(String[] args) throws IOException {
        int[] arr = {5, 4, 2, 3, 3, 0, 12, 22, 23, 44, 3, 2, 54};
        System.out.println(arr.length);
        new Person().sort(arr, 0, arr.length - 1);
        System.out.println(arr[0]);
    }

    void merge(int[] arr, int l, int m, int r) {
        int[] arrLeft = new int[m - l + 1];
        int[] arrRight = new int[r - m];

        for (int i = 0; i < m - l + 1; i++)
            arrLeft[i] = arr[i + l];
        for (int i = 0; i < r - m; i++)
            arrRight[i] = arr[i + m + 1];

        int i = 0, j = 0;
        int k = l;

        while ((i < m - l + 1) && (j < r - m)) {
            if (arrLeft[i] < arrRight[j]) {
                arr[k] = arrLeft[i];
                i++;
            } else {
                arr[k] = arrRight[j];
                j++;
            }
            k++;
        }

        while (i < m - l + 1) {
            arr[k] = arrLeft[i];
            k++;
            i++;
        }
        while (j < r - m) {
            arr[k] = arrRight[j];
            k++;
            j++;
        }
    }

    void sort(int[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            sort(arr, l, m);
            sort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

}
