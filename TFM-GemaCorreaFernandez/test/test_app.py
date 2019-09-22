# -------------------------------- test_app.py ------------------------------- #

# Autora: Gema Correa Fernández

'''
Fichero que testea la app
'''

# Bibliotecas a usar
import unittest     # https://docs.python.org/3/library/unittest.html
# https://www.blog.pythonlibrary.org/2016/07/07/python-3-testing-an-intro-to-unittest/
import requests     # https://www.pythonforbeginners.com/requests/using-requests-in-python


class appTestCase(unittest.TestCase):

# ---------------------------------------------------------------------------- #

    # Si el método setUp() hace una excepción mientras se ejecuta la prueba,
    # el framework considerará que la prueba ha sufrido un error y el método
    # de prueba no se ejecutará.
    def setUp(self):
        pass

 # --------------------------------------------------------------------------

    # Testear que se ha desplegado correctamente
    def test1_index(self):
        result = requests.get('http://127.0.0.1:7955')
        self.assertEqual(result.status_code, 200)

        pass

# ---------------------------------------------------------------------------- #

if __name__ == '__main__':
    unittest.main()
