import random
import sys


def finalize_error():
    """Imprime vacio y termina el programa con error"""
    print('')
    exit(-1)


def main():
    """Proceso principal"""
    field = sys.argv[1] if len(sys.argv) > 1 else None

    # Si no enviaron datos, imprimo vacio y termino con error
    if field is None:
       finalize_error()

    # Si piden el pvalor, envio entre 0 y 0.5 para demostrar que podemos leer desde python
    # un valor enviado desde Java
    if field == 'pvalue':
        print(random.uniform(0, 0.5))
    elif field == 'statistical':
        # Devuelvo el estadistico
        print(random.uniform(0.5, 1))
    else:
        # Si no es un campo valido, informo error
        finalize_error()


if __name__ == '__main__':
    main()
