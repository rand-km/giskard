import logging

from ml_worker.core.files_utils import read_model_file, read_dataset_file

from generated.ml_worker_pb2 import RunTestRequest, TestResultMessage
from generated.ml_worker_pb2_grpc import MLWorkerServicer
from ml_worker.testing.functions import GiskardTestFunctions
from mltask_server_runner import settings

logger = logging.getLogger()


class MLTaskServer(MLWorkerServicer):
    models_store = {}
    counter = 0

    def __init__(self, start_counter=0) -> None:
        super().__init__()
        self.counter = start_counter

    def runTest(self, request: RunTestRequest, context) -> TestResultMessage:
        root = settings.storage_root
        model_path = root / request.model_path

        tests = GiskardTestFunctions()
        _globals = {
            'model': read_model_file(str(model_path.absolute())),
            'tests': tests
        }
        if request.train_dataset_path:
            _globals['train_df'] = read_dataset_file(str((root / request.train_dataset_path).absolute()))
        if request.test_dataset_path:
            _globals['test_df'] = read_dataset_file(str((root / request.test_dataset_path).absolute()))

        exec(request.code, _globals)

        return TestResultMessage(results=tests.tests_results)