APP_SPEC_DIR=swagger/unicorns-spec
AUTH_SPEC_DIR=swagger/auth-spec

cd ${AUTH_SPEC_DIR}
mvn  test
cd ../../${APP_SPEC_DIR}
mvn  test
