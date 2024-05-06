import tkinter as tk
from tkinter import filedialog, messagebox
import requests
import pandas as pd
import traceback

def upload_excel():
    # Abrir a caixa de diálogo para selecionar o arquivo Excel
    file_path = filedialog.askopenfilename(filetypes=[("Arquivos Excel", "*.xlsx")])

    if file_path:
        try:
            # Enviar o arquivo Excel para a API e obter os dados processados
            url = 'http://localhost:8082/processar-excel'
            files = {'file': open(file_path, 'rb')}
            response = requests.post(url, files=files)
            data = response.json()

            # Verificar o resultado da API
            if 'error' in data:
                messagebox.showerror("Erro", data['error'])
            else:
                messagebox.showinfo("Sucesso", "Arquivo processado com sucesso")
                # Enviar os dados processados para a API do Java
                enviar_dados_processados(data['data'])

        except Exception as e:
            messagebox.showerror("Erro", str(e))

            def enviar_dados_processados(data):
    try:
        print(data)
        # Converter os dados processados em um DataFrame do pandas
        df = pd.DataFrame(data, dtype=str).fillna('')

        print(df.dtypes)

        df_filtered = df[['vazio', 'bloco', 'apartamento']]

        # Dicionário para mapeamento de valores
        dicionario_mapeamento = {"Sim": True, "Não": False, "Nao": False}

        # Mapeando os valores
        df_filtered['vazio'] = df_filtered['vazio'].map(dicionario_mapeamento)

        # Convertendo a coluna 'vazio' para tipo booleano
        df_filtered['vazio'] = df_filtered['vazio'].map({True: 'true', False: 'false'})

        print(df_filtered.to_dict(orient='records'))

        # Enviar os dados para a API Java
        url = 'http://localhost:8080/apartamentos'
        response = requests.post(url, json=df_filtered.to_dict(orient='records'))

        # Verificar o resultado da API Java
        if response.status_code == 200:
            messagebox.showinfo("Sucesso", "Dados enviados com sucesso para a API Java")
        else:
            messagebox.showerror("Erro", "Falha ao enviar os dados para a API Java")

    except Exception as e:
        messagebox.showerror("Erro", str(e))
        print(traceback.format_exc())
        raise e

    # Criar a janela principal
root = tk.Tk()
root.title("Upload de Arquivo Excel")

# Botão para selecionar o arquivo Excel
upload_button = tk.Button(root, text="Selecionar Arquivo Excel", command=upload_excel)
upload_button.pack(pady=20)

# Executar a interface gráfica
root.mainloop()