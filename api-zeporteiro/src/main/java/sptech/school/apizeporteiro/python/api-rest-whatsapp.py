from flask import Flask, request, jsonify
import pywhatkit

app = Flask(__name__)

@app.route('/enviar-mensagem', methods=['POST'])
def enviar_mensagem():
    data = request.json
    numero = data['numero']
    hora = data['hora']
    minuto = data['minuto']

    mensagem = "Olá! Sua entrega chegou! Venha Retirar"

    try:
        # Envia a mensagem do WhatsApp
        pywhatkit.sendwhatmsg(numero, mensagem, hora, minuto)
        return jsonify({'success': True, 'message': 'Mensagem enviada com sucesso!'}), 200
    except Exception as e:
        return jsonify({'success': False, 'error': str(e)}), 500

if __name__ == '__main__':
    app.run(port=8081)