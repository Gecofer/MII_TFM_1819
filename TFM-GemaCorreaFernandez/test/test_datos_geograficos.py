# ------------------------- test_datos_geograficos.py ------------------------ #

# Autora: Gema Correa Fernández

import unittest
import pyexcel as pe

'''
Fichero que testea los excel
'''

class geometriasTestCase(unittest.TestCase):

    # Si el método setUp() hace una excepción mientras se ejecuta la prueba,
    # el framework considerará que la prueba ha sufrido un error y el método
    # de prueba no se ejecutará.
    def setUp(self):
        pass

# ---------------------------------------------------------------------------- #

    # Testear el contenido del fichero CurvaNivel
    def test_curvas_nivel(self):

        curvas_nivel = pe.get_sheet(file_name = "datos/CurvaNivel.xlsx")
        new_row_names = curvas_nivel.row[1]
        new_row_names1 = curvas_nivel.row[2]

        # Check first and last name fields
        self.assertEqual(new_row_names[0], "WKT", "The content of the fields doesn't match")
        self.assertEqual(new_row_names[-1], "COTA", "The content of the fields doesn't match")

        self.assertEqual(new_row_names1[0], "LINESTRING (440675.4 4106319.93,440670.97 4106322.69,440666.96 4106326.35,440665.15 4106330.44,440664.95 4106335.46,440666.77 4106340.7,440669.25 4106342.7,440674.38 4106344.42,440679.8 4106344.52,440685.13 4106343.42,440690.32 4106341.32,440694.75 4106338.61,440698.49 4106335.0,440700.62 4106330.14,440700.19 4106326.38,440698.02 4106323.2,440694.1 4106320.67,440689.16 4106319.53,440675.4 4106319.93)",
                        "The content of the fields doesn't match")

        pass

# ---------------------------------------------------------------------------- #

    # Testear el contenido del fichero Edificaciones
    def test_edificaciones(self):

        edificaciones = pe.get_sheet(file_name = "datos/Edificaciones.xlsx")
        new_row_names = edificaciones.row[1]
        new_row_names1 = edificaciones.row[2]

        # Check first and last name fields
        self.assertEqual(new_row_names[0], "WKT", "The content of the fields doesn't match")
        self.assertEqual(new_row_names[-1], "ESTADO", "The content of the fields doesn't match")

        self.assertEqual(new_row_names1[0], "POLYGON ((446020.74 4107035.28,446022.37 4107043.27,446028.27 4107042.07,446026.64 4107034.08,446020.74 4107035.28))",
                        "The content of the fields doesn't match")

        pass

# ---------------------------------------------------------------------------- #

    # Testear el contenido del fichero PuntoCota
    def test_punto_cota(self):

        punto_cota = pe.get_sheet(file_name = "datos/PuntoCota.xlsx")
        new_row_names = punto_cota.row[1]
        new_row_names1 = punto_cota.row[2]

        # Check first and last name fields
        self.assertEqual(new_row_names[0], "WKT", "The content of the fields doesn't match")
        self.assertEqual(new_row_names[-1], "COTA", "The content of the fields doesn't match")

        self.assertEqual(new_row_names1[0], "MULTIPOINT ((446228.92 4108432.53))",
                        "The content of the fields doesn't match")

        pass

# ---------------------------------------------------------------------------- #

if __name__ == '__main__':
    unittest.main()
