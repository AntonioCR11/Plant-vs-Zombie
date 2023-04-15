package com.company;

import javax.swing.*;
import java.awt.*;

public class ImageScaler {

    // Mengubah ukuran dari gambar yang di-inputkan sebagai parameter
    public static ImageIcon getScaledIcon(ImageIcon srcImg, int width, int height) {
        ImageIcon imageIcon = srcImg; // Ambil gambar yang akan di-scale
        Image image = imageIcon.getImage(); // Ubah objek menjadi Image
        Image newIcon = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale ddengan halus
        imageIcon = new ImageIcon(newIcon);  // Kembalikan menjadi ImageIcon
        return imageIcon;
    }
}
