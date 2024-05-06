from flask import Flask, request, jsonify
import pandas as pd

app = Flask(__name__)

@app.route('/processar-excel', methods=['POST'])
def processar_excel():
    # Verifica se o arquivo Excel foi enviado na requisição
    if 'file' not in request.files:
        return jsonify({'error': 'Nenhum arquivo enviado'}), 400

    # Lê o arquivo Excel enviado
    excel_file = request.files['file']

    # Verifica se o arquivo Excel é do tipo esperado
    if not excel_file.filename.endswith('.xlsx'):
        return jsonify({'error': 'Formato de arquivo inválido. Envie um arquivo Excel (.xlsx)'}), 400

    try:
        # Lê o arquivo Excel para um DataFrame do Pandas
        df = pd.read_excel(excel_file, dtype=str)

        # Verifica se o DataFrame possui todas as colunas esperadas
        expected_columns = ['bloco', 'apartamento', 'nome morador', 'número whatsapp 1', 'número whatsapp 2', 'número whatsapp 3', 'vazio?']
        if not set(expected_columns).issubset(set(df.columns)):
            return jsonify({'error': 'O arquivo Excel não possui todas as colunas esperadas'}), 400

        for column in ['número whatsapp 1', 'bloco', 'apartamento', 'nome morador', 'vazio?']:
            if df[column].isnull().any():
                return jsonify({'error': f'A coluna "{column}" não pode ter registros nulos ou vazios'}), 400

        # Verifica se qualquer registro das colunas de whatsapp tem 14 dígitos
        for column in ['número whatsapp 1', 'número whatsapp 2', 'número whatsapp 3']:
            if df[column].isnull().any():
                continue
            if (df[column].str.len() != 14).any():
                # Encontra as linhas com registros que não possuem 14 dígitos
                invalid_rows = df[df[column].str.len() != 14].index.tolist()
                # Retorna o erro com a linha e a coluna que deu erro
                return jsonify({'error':
                                    f'Há registros na coluna "{column}" que não possuem 14 dígitos, lembre-se de colocar o "+55" antes do DDD e adicionar também o DDD no número de telefone.Linhas: {invalid_rows}'}), 400


        # Verifica se a coluna 'vazio?' contém apenas os valores ["sim", "não"]
        if not df['vazio?'].str.lower().isin(['sim', 'não', 'nao']).all():
            invalid_rows = df[~df['vazio?'].isin(['sim', 'não'])].index.tolist()
            return jsonify({'error': f'A coluna "vazio?" deve conter apenas os valores "sim" ou "não". Linhas: {invalid_rows}'}), 400



        df = df.rename(columns={'nome morador': 'nomeMorador',
                                'vazio?': 'vazio',
                                'número whatsapp 1': 'numeroWhat1',
                                'número whatsapp 2': 'numeroWhat2',
                                'número whatsapp 3': 'numeroWhat3'})

        print(df)
        print(df.columns)

        # Transforma o DataFrame em uma lista de dicionários
        data = df.to_dict(orient='records')

        print(data)

        # Retorna os dados processados
        return jsonify({'success': True, 'data': data}), 200

    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(port=8082)
