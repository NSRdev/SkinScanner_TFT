import io

import flask
from fastai.vision import *

app = flask.Flask(__name__)


def load_my_model():
    global learn
    learn = load_learner('/home/nahudel98/tfg_data/')


@app.route("/predict", methods=["POST"])
def predict_model_1():

    if flask.request.method == "POST":
        if flask.request.files.get("image"):
            image = flask.request.files["image"].read()
            image = open_image(io.BytesIO(image))

            result = learn.predict(image)[0]

            f = open("status.txt", "a+")
            f.write(str(result) + "\n")

    return str(result)


if __name__ == "__main__":
    load_my_model()
    app.run(host='0.0.0.0', threaded=False)
