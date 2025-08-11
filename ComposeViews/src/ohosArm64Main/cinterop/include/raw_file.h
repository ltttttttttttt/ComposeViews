#ifndef GLOBAL_RAW_FILE_H
#define GLOBAL_RAW_FILE_H

#ifdef __cplusplus
extern "C" {
#endif

struct RawFile;

typedef struct RawFile RawFile;

int OH_ResourceManager_ReadRawFile(const RawFile *rawFile, void *buf, size_t length);

long OH_ResourceManager_GetRawFileSize(RawFile *rawFile);

void OH_ResourceManager_CloseRawFile(RawFile *rawFile);

#ifdef __cplusplus
};
#endif

/** @} */
#endif // GLOBAL_RAW_FILE_H
